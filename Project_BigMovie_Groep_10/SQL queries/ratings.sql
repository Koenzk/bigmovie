-- Table: public.ratings

-- DROP TABLE public.ratings;

CREATE TABLE public.ratings
(
    tconst character varying(9) COLLATE pg_catalog."default" NOT NULL,
    average_rating real NOT NULL,
    num_votes integer NOT NULL,
    CONSTRAINT foreignkey_title_const FOREIGN KEY (tconst)
        REFERENCES public.titles (tconst) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.ratings
    OWNER to postgres;