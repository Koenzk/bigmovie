-- Table: public.titles_episodes

-- DROP TABLE public.titles_episodes;

CREATE TABLE public.titles_episodes
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    parent_tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    season_number integer,
    episode_number integer,
    CONSTRAINT foreignkey_parent_title_const FOREIGN KEY (parent_tconst)
        REFERENCES public.titles (tconst) MATCH SIMPLE
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

ALTER TABLE public.titles_episodes
    OWNER to postgres;
COMMENT ON TABLE public.titles_episodes
    IS 'Koppeltabel op de title tabel zelf. De titles tabel bevat zelf de episodes ook, deze tabel is er om de episodes te koppelen aan zijn ''parent'' en extra informatie te verschaffen. ';