-- Table: public.crew

-- DROP TABLE public.crew;

CREATE TABLE public.crew
(
    tconst character varying COLLATE pg_catalog."default" NOT NULL,
    directors text COLLATE pg_catalog."default",
    writers text COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.crew
    OWNER to postgres;