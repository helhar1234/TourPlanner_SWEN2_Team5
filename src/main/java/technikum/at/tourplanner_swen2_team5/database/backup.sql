--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE tour_planner_g5;




--
-- Drop roles
--

DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:h3+scFCFauTxfTKFjEoAVg==$liY8VJJ6/I+q0U83s/Lus+ku4yCLGiXxo+sJyQAtZC0=:IfDHh6FkMGHioYpVR4PlSAVg4o52qYtR4KCGSvp4F5M=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

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

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

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

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

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

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

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

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

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

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- Database "tour_planner_g5" dump
--

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

--
-- Name: tour_planner_g5; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tour_planner_g5 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE tour_planner_g5 OWNER TO postgres;

\connect tour_planner_g5

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
-- Name: difficultys; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.difficultys (
    difficultyid integer NOT NULL,
    difficulty character varying(255)
);


ALTER TABLE public.difficultys OWNER TO postgres;

--
-- Name: maps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maps (
    tourid_fk character varying(255) NOT NULL,
    filename character varying(255) NOT NULL
);


ALTER TABLE public.maps OWNER TO postgres;

--
-- Name: tourlogs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tourlogs (
    tourlogid character varying(255) NOT NULL,
    tourid_fk character varying(255) NOT NULL,
    date timestamp without time zone NOT NULL,
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
-- Data for Name: difficultys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.difficultys (difficultyid, difficulty) FROM stdin;
1	Easy
2	Moderate
3	Challenging
4	Difficult
\.


--
-- Data for Name: maps; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.maps (tourid_fk, filename) FROM stdin;
\.


--
-- Data for Name: tourlogs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tourlogs (tourlogid, tourid_fk, date, "time", comment, difficultyid, distance, totaltime, rating, transporttypeid_fk) FROM stdin;
\.


--
-- Data for Name: tours; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tours (tourid, name, start, destination, transporttypeid_fk, distance, "time") FROM stdin;
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
-- Name: difficultys difficultys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.difficultys
    ADD CONSTRAINT difficultys_pkey PRIMARY KEY (difficultyid);


--
-- Name: maps maps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maps
    ADD CONSTRAINT maps_pkey PRIMARY KEY (tourid_fk);


--
-- Name: tourlogs tourlogs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tourlogs
    ADD CONSTRAINT tourlogs_pkey PRIMARY KEY (tourlogid);


--
-- Name: tours tours_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_name_key UNIQUE (name);


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
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

