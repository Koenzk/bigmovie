-- Table: public.ratings

-- DROP TABLE public.ratings;

CREATE TABLE public.ratings
(
    title_id character varying(9) COLLATE pg_catalog."default" NOT NULL,
    average_rating double precision NOT NULL,
    num_votes bigint NOT NULL,
    CONSTRAINT "title.ratings.tsv_pkey" PRIMARY KEY (title_id),
    CONSTRAINT "RatingsTconst_fkey" FOREIGN KEY (title_id)
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