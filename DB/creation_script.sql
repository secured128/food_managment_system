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

CREATE TABLE public.product
  (
    id bigint NOT NULL,
    code1 bigint,
    code2 bigint,
    description character varying(255),
    id_unit bigint NOT NULL REFERENCES units(id),
    id_supplier bigint NOT NULL REFERENCES SUPPLIERS(id),
    quantity_in_package NUMERIC NOT NULL ,
    CONSTRAINT product_pkey PRIMARY KEY (id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.product
    OWNER TO food_manager;

CREATE TABLE public.recipe
(
  id bigint NOT NULL,
  name character varying(255),
  description character varying(255),
  picture bytea,
  CONSTRAINT recipe_pkey PRIMARY KEY (id),
  CONSTRAINT recipe_unique_idx UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.recipe
  OWNER TO food_manager;

CREATE TABLE public.units
(
  id bigint NOT NULL,
  unit_name character varying(255),
  CONSTRAINT units_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.units
  OWNER TO food_manager;
