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
    CONSTRAINT product_pkey PRIMARY KEY (id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.product
    OWNER TO food_manager;

CREATE TABLE public.recipe_description
(
  id bigint NOT NULL,
  recipe_name character varying(255),
  description character varying(255),
  image bytea,
  size_of_recipe numeric,
  unit character varying(255) NOT NULL REFERENCES units(unit_name),
  CONSTRAINT recipe_description_pkey PRIMARY KEY (id),
  CONSTRAINT recipe_description_unique_idx UNIQUE (recipe_name)
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

  CREATE TABLE public.stock
    (
      id bigint NOT NULL,
      product_id bigint NOT NULL,
      quantity_in_package numeric NOT NULL,
      package_volume numeric NOT NULL,
      package_unit character varying(255) NOT NULL,
      price_per_package numeric NOT NULL,
      total_price numeric NOT NULL,
      quantity_for_recipes numeric NOT NULL,
      creation_date date not null,
      expiration_date date not null,
      placement character varying(255) NOT NULL,
      used_quantity numeric  NOT NULL,
      CONSTRAINT stock_pkey PRIMARY KEY (id)

    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.stock
      OWNER TO food_manager;



     CREATE TABLE public.transaction_log
    (
      id bigint NOT NULL,
      transaction_id bigint NOT NULL,
      user_email character varying(255) NOT NULL,
      transaction_type character varying(255) NOT NULL,
      creation_date timestamp without time zone not null,
      recipe_name  character varying(255) NOT NULL,
      recipe_quantity numeric NOT NULL,
      recipe_unit character varying(255) NOT NULL,
      stock_id bigint NOT NULL ,
      used_quantity numeric NOT NULL,
      cancel_ind character(1),
      cancellation_time timestamp without time zone,
      CONSTRAINT transaction_log_pkey PRIMARY KEY (id)
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.transaction_log
      OWNER TO food_manager;
    create sequence transaction_log_seq
    grant all on sequence transaction_log_seq to food_manager


  CREATE TABLE public.measure_conversion
    (  id bigint NOT NULL,
      from_unit character varying(255) NOT NULL,
      to_unit character varying(255) NOT NULL,
      conversion_value numeric NOT NULL
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.measure_conversion
      OWNER TO food_manager;


create sequence hibernate_sequence


grant all on sequence hibernate_sequence to food_manager





drop table recipe_products;
drop table recipe_instructions_order;
drop table instructions_description;
drop table recipe_description;
drop table product;
drop table units;
drop table suppliers;
drop table measure_translation;

drop table stock;
drop table transaction_log;