-- Table: public.titles

-- DROP TABLE public.titles;

CREATE TABLE public.titles
(
    tconst character varying COLLATE pg_catalog."default" NOT NULL,
    title_type character varying COLLATE pg_catalog."default",
    primary_title character varying COLLATE pg_catalog."default",
    original_title character varying COLLATE pg_catalog."default",
    is_adult integer,
    start_year integer,
    end_year integer,
    runtime_mins integer,
    genres character varying COLLATE pg_catalog."default",
    CONSTRAINT "Titles_pkey" PRIMARY KEY (tconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.titles
    OWNER to postgres;