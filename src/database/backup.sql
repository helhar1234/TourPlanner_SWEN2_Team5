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
    id bigint NOT NULL
);


ALTER TABLE public.maps OWNER TO postgres;

--
-- Name: tourlogs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tourlogs (
    tourlogid character varying(255) NOT NULL,
    tourid_fk character varying(255) NOT NULL,
    date character varying(255) NOT NULL,
    "time" character varying(255),
    comment character varying(255),
    difficultyid integer DEFAULT 0,
    distance character varying(255),
    totaltime character varying(255),
    rating integer DEFAULT 0,
    transporttypeid_fk integer DEFAULT 0
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
\.


--
-- Data for Name: tourlogs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tourlogs (tourlogid, tourid_fk, date, "time", comment, difficultyid, distance, totaltime, rating, transporttypeid_fk) FROM stdin;
bae6d53c-df84-4768-96f3-cc3af3c7c5ad	b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	null	00:00	jwebckwjdb	4	kdjcn	0h 0min	3	2
1435063f-d1bd-4167-b34f-f52edca3f08a	b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	29.05.2024	14:00	dkcnfked\ndjcbsd vjcs	3	1	0h 0min	5	3
9ee7b8a4-60a6-45c1-9142-2d909b912578	b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	29.05.2024	14:00	dkcefkmv	2	2	0h 0min	5	3
3e229611-2ad5-4cdb-9390-7459ef7a4afd	b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	30.05.2024	14:00	kdmf	3	3	1h 0min	5	2
acfa0c68-1ccc-451f-9813-ab4f77837c56	c2e8db77-7d5d-4cfb-9efb-3d849f3c1a33	06.03.2024	04:12	lfkvkdc\nasdkvnfjv	4	23	0h 0min	9	2
2a54bb23-4b24-4644-90d5-e02b0d09896a	252a70d2-d6c5-4a9c-9abc-6b2bc8103efa	27.05.2024	15:00	kf vjc	2	13	1h 4min	5	2
7f310205-451d-4272-b78e-459bade55191	252a70d2-d6c5-4a9c-9abc-6b2bc8103efa	28.05.2024	14:00	jv jlef4evkicfejl	2	3	1h 0min	5	2
c904e605-25fa-4beb-b06f-94a8c88513f0	252a70d2-d6c5-4a9c-9abc-6b2bc8103efa	30.05.2024	14:00	jdks dls	2	5	0h 5min	5	3
251b9677-eb79-4d1a-8de4-c7f25e363e9a	b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	27.05.2024	23:00	ldkvm├Âskm	2	7.3	0h 4min	10	4
\.


--
-- Data for Name: tours; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tours (tourid, name, description, start, destination, transporttypeid_fk, distance, "time") FROM stdin;
b1f3cb88-8c7e-47f9-a6fc-4ea4fa2b6a22	Tour 2	description...	Start Location 2	Destination 2	2	20	120
c2e8db77-7d5d-4cfb-9efb-3d849f3c1a33	Tour 3	description...	Start Location 3	Destination 3	3	30	180
d3f1ec66-6e4c-40eb-8dfb-2c839e2b2b44	Tour 4	description...	Start Location 4	Destination 4	4	40	240
252a70d2-d6c5-4a9c-9abc-6b2bc8103efa	test test	jhabcjksd\nasdja sv\nash	Wien	Graz	2	0	0
212cb6aa-5429-4eca-b095-c695d2c39d2a	Tour 1.1	adbcsjbd\navujbadjv\najsbvja	Wien	Graz	3	0	0
0e433d30-e706-4644-aa1e-1e5e4f22eee4	hdcnkj	dksvb	kjdbvk	jhsdvb	4	0	0
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

