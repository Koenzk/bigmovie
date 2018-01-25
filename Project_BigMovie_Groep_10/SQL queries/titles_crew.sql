-- Table: public.titles_crew

-- DROP TABLE public.titles_crew;

CREATE TABLE public.titles_crew
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    nconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    role character varying(8) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT foreignkey_name_const FOREIGN KEY (nconst)
        REFERENCES public.names (nconst) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreignkey_title_const FOREIGN KEY (tconst)
        REFERENCES public.titles (tconst) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.titles_crew
    OWNER to postgres;