START TRANSACTION;

DROP FUNCTION IF EXISTS JdbcGetEmailByContact(VARCHAR, VARCHAR);
DROP FUNCTION IF EXISTS JdbcGetEmailsByAbreviation(VARCHAR);
DROP FUNCTION IF EXISTS JdbcGetTagsByContact(VARCHAR, VARCHAR);
DROP FUNCTION IF EXISTS JdbcCreateContact(VARCHAR, VARCHAR, VARCHAR, VARCHAR);
DROP FUNCTION IF EXISTS JdbcAddTagToContact(INTEGER, VARCHAR);
DROP FUNCTION IF EXISTS JdbcRemoveContact(INTEGER);

CREATE FUNCTION JdbcGetEmailByContact(prenom VARCHAR, nom VARCHAR)
	RETURNS VARCHAR LANGUAGE PLPGSQL AS $$
	DECLARE 
		mail VARCHAR;
	BEGIN
		SELECT INTO mail c.email FROM contacts c WHERE LOWER(c.prenom) = LOWER($1) AND LOWER(c.nom) = LOWER($2);
		RETURN mail;
	END;
$$;

CREATE FUNCTION JdbcGetEmailsByAbreviation(abreviation VARCHAR) 
	RETURNS SETOF VARCHAR LANGUAGE PLPGSQL AS $$
	BEGIN
		RETURN QUERY SELECT c.email FROM contacts c JOIN pays p ON c.pays = p.id WHERE LOWER(p.abreviation) = LOWER($1);
	END;
$$;

CREATE FUNCTION JdbcGetTagsByContact(prenom VARCHAR, nom VARCHAR)
	RETURNS SETOF VARCHAR LANGUAGE PLPGSQL AS $$
	BEGIN
		RETURN QUERY SELECT t.tag FROM tags t JOIN contacts_tags ct ON t.id = ct.tag JOIN contacts c ON ct.contact = c.id
				WHERE LOWER(c.prenom) = LOWER($1) AND LOWER(c.nom) = LOWER($2);
	END;
$$;

CREATE FUNCTION JdbcCreateContact(prenom VARCHAR, nom VARCHAR, email VARCHAR, abreviationPays VARCHAR) 
	RETURNS INTEGER LANGUAGE PLPGSQL AS $$
	DECLARE
		idContact INTEGER := 0;
		idPays INTEGER;
		goInsert BOOLEAN := FALSE;
	BEGIN
		IF $1 IS NOT NULL AND $2 IS NOT NULL AND $3 IS NOT NULL THEN
			PERFORM c.id FROM contacts c WHERE LOWER(c.prenom) = LOWER($1) AND LOWER(c.nom) = LOWER($2);
			IF NOT FOUND THEN
				IF $4 IS NOT NULL THEN
					SELECT INTO idPays p.id FROM pays p WHERE LOWER(p.abreviation) = LOWER($4);
				END IF;
				IF idPays IS NOT NULL OR abreviationPays IS NULL THEN
					INSERT INTO contacts(prenom, nom, email, pays) VALUES($1, $2, $3, idPays);
					idContact := CURRVAL('contacts_id_seq');
				END IF;
			END IF;
		END IF;

		RETURN idContact;
	END;
$$;

CREATE FUNCTION JdbcAddTagToContact(idContact INTEGER, tagValue VARCHAR)
	RETURNS VOID LANGUAGE PLPGSQL AS $$
	DECLARE
		idTag INTEGER;
	BEGIN
		IF idContact IS NULL OR tagValue IS NULL THEN
			RAISE EXCEPTION 'les paramÃ¨tres ne peuvent pas etre null';
		ELSE
			SELECT INTO idTag t.id FROM tags t WHERE LOWER(t.tag) = LOWER($2);
			IF idTag IS NULL THEN
				INSERT INTO tags(tag) VALUES($2);
				idTag := CURRVAL('tags_id_seq');
			END IF; 
			INSERT INTO contacts_tags(contact, tag) VALUES($1, idTag);
		END IF;
	END;
$$;

CREATE FUNCTION JdbcRemoveContact(idContact INTEGER) 
	RETURNS VOID LANGUAGE PLPGSQL AS $$
	BEGIN
		DELETE FROM contacts WHERE id = $1;
	END;
$$;

COMMIT;