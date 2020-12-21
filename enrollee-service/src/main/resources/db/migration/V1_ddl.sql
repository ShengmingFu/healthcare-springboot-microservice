CREATE TABLE public.enrollees
(
    id bigint NOT NULL DEFAULT nextval('enrollees_id_seq'::regclass),
    activation boolean NOT NULL,
    birth_date date NOT NULL,
    name character varying(255) NOT NULL,
    phone_number character varying(255),
    CONSTRAINT enrollees_pkey PRIMARY KEY (id)
);

CREATE TABLE public.dependents
(
    id bigint NOT NULL DEFAULT nextval('dependents_id_seq'::regclass),
    birth_date date NOT NULL,
    name character varying(255) NOT NULL,
    enrollee_id bigint,
    CONSTRAINT dependents_pkey PRIMARY KEY (id),
    CONSTRAINT fk11t7aucghlm28fxnovyefkg6t FOREIGN KEY (enrollee_id)
        REFERENCES public.enrollees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)