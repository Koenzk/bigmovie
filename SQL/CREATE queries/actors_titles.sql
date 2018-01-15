-- Table: public.actors_titles

-- DROP TABLE public.actors_titles;

CREATE TABLE public.actors_titles
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    nconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
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

ALTER TABLE public.actors_titles
    OWNER to postgres;
COMMENT ON TABLE public.actors_titles
    IS 'Koppeltabel tussen titles en acteurs/actrices. Veel op veel relatie.';