-- Table: public.names

-- DROP TABLE public.names;

CREATE TABLE public.names
(
    nconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    primary_name text COLLATE pg_catalog."default" NOT NULL,
    birth_year integer,
    death_year integer,
    primary_profession text COLLATE pg_catalog."default",
    CONSTRAINT names_pkey PRIMARY KEY (nconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.names
    OWNER to postgres;