-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE users
    OWNER to food_manager;

-- Index: user_unique_idx

-- DROP INDEX user_unique_idx;

CREATE UNIQUE INDEX user_unique_idx
    ON users USING btree(name);


-- Table: public.suppliers

-- DROP TABLE public.suppliers;

CREATE TABLE public.suppliers
(
  id bigint NOT NULL,
  name character varying(255),
  address character varying(255),
  city character varying(255),
  telephone_num character varying(255),
  fax character varying(255),
  email character varying(255),
  company_no character varying(255),
  CONSTRAINT suppliers_pkey PRIMARY KEY (id),
  CONSTRAINT suppliers_unique_idx UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.suppliers
  OWNER TO food_manager;
