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

  CREATE TABLE public.units
  (
    unit_name character varying(255),
    CONSTRAINT units_pkey PRIMARY KEY (unit_name)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.units
    OWNER TO food_manager;


CREATE TABLE public.product
  (
    id bigint NOT NULL,
    code1 bigint,
    code2 bigint,
    description character varying(255),
    unit character varying(255) NOT NULL REFERENCES units(unit_name),
    id_supplier bigint NOT NULL REFERENCES SUPPLIERS(id),
    quantity_in_package NUMERIC NOT NULL ,
    CONSTRAINT product_pkey PRIMARY KEY (id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.product
    OWNER TO food_manager;

CREATE TABLE public.recipe_description
(
  recipe_name character varying(255),
  description character varying(255),
  image bytea,
  size_of_recipe numeric,
  unit character varying(255) NOT NULL REFERENCES units(unit_name),
  CONSTRAINT recipe_description_pkey PRIMARY KEY (recipe_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.recipe_description
  OWNER TO food_manager;

CREATE TABLE public.instructions_description
(
  instruct_name character varying(255),
  instruction_description character varying(255),
  image bytea,
    CONSTRAINT instructions_description_pkey PRIMARY KEY (instruct_name)

)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.instructions_description
  OWNER TO food_manager;

  CREATE TABLE public.recipe_instructions_order
  (
    id bigint NOT NULL,
    recipe_description_name character varying(255)  NOT NULL REFERENCES recipe_description(recipe_name),
    instruction_name  character varying(255) NOT NULL REFERENCES instructions_description(instruct_name),
    linked_recipe_desc_name character varying(255)  REFERENCES recipe_description(recipe_name),
    quantity_of_linked_recipe numeric,
    instruction_order integer,
    CONSTRAINT recipe_instructions_order_pkey PRIMARY KEY (id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.recipe_instructions_order
    OWNER TO food_manager;

    CREATE TABLE public.recipe_products
    (
      id bigint NOT NULL,
      recipe_desc_name  character varying(255) NOT NULL REFERENCES recipe_description(recipe_name),
      product_id bigint NOT NULL REFERENCES product(id),
      quantity numeric NOT NULL,
      CONSTRAINT recipe_products_pkey PRIMARY KEY (id)
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.recipe_products
      OWNER TO food_manager;


drop table recipe_products;
drop table recipe_instructions_order;
drop table instructions_description;
drop table recipe_description;
drop table product;
drop table units;
drop table suppliers;