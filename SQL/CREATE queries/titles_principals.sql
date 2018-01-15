-- Table: public.titles_principals

-- DROP TABLE public.titles_principals;

CREATE TABLE public.titles_principals
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

ALTER TABLE public.titles_principals
    OWNER to postgres;
COMMENT ON TABLE public.titles_principals
    IS 'Koppeltabel tussen titles and principals. Welke acteurs/actrices hebben hoofdrollen gespeeld in welke films. Veel op veel relatie.';