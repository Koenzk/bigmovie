-- Table: public.titles

-- DROP TABLE public.titles;

CREATE TABLE public.titles
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    title_type character varying COLLATE pg_catalog."default" NOT NULL,
    primary_title text COLLATE pg_catalog."default" NOT NULL,
    original_title text COLLATE pg_catalog."default",
    is_adult boolean NOT NULL,
    start_year integer,
    end_year integer,
    runtime_minutes integer,
    genres text COLLATE pg_catalog."default",
    CONSTRAINT titles_pkey PRIMARY KEY (tconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.titles
    OWNER to postgres;