-- Table: public.names

-- DROP TABLE public.names;

CREATE TABLE public.names
(
    nconst character varying COLLATE pg_catalog."default" NOT NULL,
    primary_name character varying COLLATE pg_catalog."default" NOT NULL,
    birth_year integer,
    death_year integer,
    primary_profession character varying COLLATE pg_catalog."default",
    titles character varying COLLATE pg_catalog."default",
    CONSTRAINT "Names_pkey" PRIMARY KEY (nconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.names
    OWNER to postgres;

-- Index: fki_NamesTitles_fkey

-- DROP INDEX public."fki_NamesTitles_fkey";

CREATE INDEX "fki_NamesTitles_fkey"
    ON public.names USING btree
    (titles COLLATE pg_catalog."default")
    TABLESPACE pg_default;