-- Table: public.episodes

-- DROP TABLE public.episodes;

CREATE TABLE public.episodes
(
    tconst character varying COLLATE pg_catalog."default" NOT NULL,
    parent_tconst character varying COLLATE pg_catalog."default",
    season_number integer,
    episode_number integer,
    CONSTRAINT "Episodes_pkey" PRIMARY KEY (tconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.episodes
    OWNER to postgres;

-- Index: fki_EpisodesParentTconst_fkey

-- DROP INDEX public."fki_EpisodesParentTconst_fkey";

CREATE INDEX "fki_EpisodesParentTconst_fkey"
    ON public.episodes USING btree
    (parent_tconst COLLATE pg_catalog."default")
    TABLESPACE pg_default;