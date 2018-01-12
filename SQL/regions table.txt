-- Table: public.regions

-- DROP TABLE public.regions;

CREATE TABLE public.regions
(
    title_id character varying COLLATE pg_catalog."default" NOT NULL,
    ordering integer,
    title character varying COLLATE pg_catalog."default",
    region character varying COLLATE pg_catalog."default",
    language character varying COLLATE pg_catalog."default",
    types character varying COLLATE pg_catalog."default",
    attributes character varying COLLATE pg_catalog."default",
    is_original_title integer
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.regions
    OWNER to postgres;