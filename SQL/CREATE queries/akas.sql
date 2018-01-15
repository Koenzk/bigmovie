-- Table: public.akas

-- DROP TABLE public.akas;

CREATE TABLE public.akas
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    ordering integer NOT NULL,
    title text COLLATE pg_catalog."default" NOT NULL,
    region character varying(4) COLLATE pg_catalog."default",
    language character varying(4) COLLATE pg_catalog."default",
    types character varying(20) COLLATE pg_catalog."default",
    attributes text COLLATE pg_catalog."default",
    is_original_title boolean,
    CONSTRAINT foreignkey_title_const FOREIGN KEY (tconst)
        REFERENCES public.titles (tconst) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.akas
    OWNER to postgres;