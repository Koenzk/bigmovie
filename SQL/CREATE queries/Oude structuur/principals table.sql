-- Table: public.principals

-- DROP TABLE public.principals;

CREATE TABLE public.principals
(
    tconst character varying COLLATE pg_catalog."default" NOT NULL,
    principal_cast character varying COLLATE pg_catalog."default",
    CONSTRAINT "Principals_pkey" PRIMARY KEY (tconst)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.principals
    OWNER to postgres;

-- Index: fki_PrincipalsNconst_fkey

-- DROP INDEX public."fki_PrincipalsNconst_fkey";

CREATE INDEX "fki_PrincipalsNconst_fkey"
    ON public.principals USING btree
    (principal_cast COLLATE pg_catalog."default")
    TABLESPACE pg_default;