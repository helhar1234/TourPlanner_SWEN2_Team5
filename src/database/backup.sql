--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: difficulties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.difficulties (
    difficultyid integer NOT NULL,
    difficulty character varying(255)
);


ALTER TABLE public.difficulties OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hibernate_sequence OWNER TO postgres;

--
-- Name: maps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maps (
    tourid_fk character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.maps OWNER TO postgres;

--
-- Name: maps_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.maps_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.maps_id_seq OWNER TO postgres;

--
-- Name: maps_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.maps_id_seq OWNED BY public.maps.id;


--
-- Name: tourlogs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tourlogs (
    tourlogid character varying(255) NOT NULL,
    tourid_fk character varying(255) NOT NULL,
    date character varying(255) NOT NULL,
    comment character varying(255),
    difficultyid integer DEFAULT 0,
    distance character varying(255),
    totaltime character varying(255),
    rating integer DEFAULT 0,
    transporttypeid_fk integer DEFAULT 0,
    timehours integer DEFAULT 0,
    timeminutes integer DEFAULT 0
);


ALTER TABLE public.tourlogs OWNER TO postgres;

--
-- Name: tours; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tours (
    tourid character varying(255) NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255) NOT NULL,
    start character varying(255) NOT NULL,
    destination character varying(255) NOT NULL,
    transporttypeid_fk integer DEFAULT 0,
    distance integer DEFAULT 0,
    "time" integer DEFAULT 0
);


ALTER TABLE public.tours OWNER TO postgres;

--
-- Name: transporttypes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transporttypes (
    transporttypeid integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.transporttypes OWNER TO postgres;

--
-- Name: maps id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maps ALTER COLUMN id SET DEFAULT nextval('public.maps_id_seq'::regclass);


--
-- Data for Name: difficulties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.difficulties (difficultyid, difficulty) FROM stdin;
1	Easy
2	Moderate
3	Challenging
4	Difficult
\.


--
-- Data for Name: maps; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.maps (tourid_fk, filename, id) FROM stdin;
76615c12-a3b7-4125-9e53-78bc40245f51	76615c12-a3b7-4125-9e53-78bc40245f51_map.png	24
bdf077c7-f715-498a-9b38-2ec9a8a95cb9	bdf077c7-f715-498a-9b38-2ec9a8a95cb9_map.png	25
\.


--
-- Data for Name: tourlogs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tourlogs (tourlogid, tourid_fk, date, comment, difficultyid, distance, totaltime, rating, transporttypeid_fk, timehours, timeminutes) FROM stdin;
356b9cc3-e07f-497f-8b68-d25521993405	76615c12-a3b7-4125-9e53-78bc40245f51	05.06.2024	It was very nice!	2	1521	25h 21min	10	4	0	0
e6540083-d9e7-45b8-83a0-0e4f2748e4de	bdf077c7-f715-498a-9b38-2ec9a8a95cb9	05.06.2024	It was awesome!	2	576	38h 24min	10	2	2	54
\.


--
-- Data for Name: tours; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tours (tourid, name, description, start, destination, transporttypeid_fk, distance, "time") FROM stdin;
76615c12-a3b7-4125-9e53-78bc40245f51	Tour 1	Description	Wien	London	4	1521	1521
bdf077c7-f715-498a-9b38-2ec9a8a95cb9	Tour 2	Nice Tour	Berlin	Köln	2	576	2304
\.


--
-- Data for Name: transporttypes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transporttypes (transporttypeid, name) FROM stdin;
1	Hike
2	Bike
3	Running
4	Vacation
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 12, true);


--
-- Name: maps_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.maps_id_seq', 25, true);


--
-- Name: difficulties difficulties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.difficulties
    ADD CONSTRAINT difficulties_pkey PRIMARY KEY (difficultyid);


--
-- Name: maps maps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maps
    ADD CONSTRAINT maps_pkey PRIMARY KEY (id);


--
-- Name: tours tours_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_pkey PRIMARY KEY (tourid);


--
-- Name: transporttypes transporttypes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transporttypes
    ADD CONSTRAINT transporttypes_pkey PRIMARY KEY (transporttypeid);


--
-- Name: tourlogs fk5gcaqo7uvqap4ddnheowf38j7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tourlogs
    ADD CONSTRAINT fk5gcaqo7uvqap4ddnheowf38j7 FOREIGN KEY (transporttypeid_fk) REFERENCES public.transporttypes(transporttypeid);


--
-- Name: tourlogs fk67427mkvq3fjn4pxm5sesxwwb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tourlogs
    ADD CONSTRAINT fk67427mkvq3fjn4pxm5sesxwwb FOREIGN KEY (difficultyid) REFERENCES public.difficulties(difficultyid);


--
-- Name: tourlogs fkrg0uewu0m9i64dd1qa1g84bcr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tourlogs
    ADD CONSTRAINT fkrg0uewu0m9i64dd1qa1g84bcr FOREIGN KEY (tourid_fk) REFERENCES public.tours(tourid);


--
-- Name: tours fktksku6hj0j71qidsnds0w4612; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT fktksku6hj0j71qidsnds0w4612 FOREIGN KEY (transporttypeid_fk) REFERENCES public.transporttypes(transporttypeid);


--
-- PostgreSQL database dump complete
--

