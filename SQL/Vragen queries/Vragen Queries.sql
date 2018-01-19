--1. Wat is de kortste film met een waardering van 8.5 of hoger?
--Getest en werkt
	SELECT primary_title
	FROM titles
	WHERE title_type = 'movie' AND tconst IN (
		SELECT tconst
		FROM ratings
		WHERE average_rating >= 8.5) 
	ORDER BY runtime_minutes ASC
	LIMIT 1;

--2. Welke regisseur heeft de meeste films met Jim Carrey in de hoofdrol geregisseerd?
--Getest en werkt
	SELECT primary_name
	FROM names
	WHERE nconst IN (
		SELECT nconst
		FROM titles_crew
		WHERE role = 'director' AND tconst IN (
			SELECT tconst 
			FROM titles_principals
			WHERE nconst IN (
				SELECT nconst 
				FROM names
				WHERE primary_name = 'Jim Carrey'
			)
			GROUP BY tconst
			ORDER BY COUNT(tconst) DESC
			LIMIT 1
		)
	);	
		
--3. Welke acteur of actrice speelt het meest in de slechtst gewaardeerde films?
--Getest en werkt
--De query duurt wel lang, 49 sec..
	SELECT primary_name
	FROM names
	WHERE nconst IN (
		SELECT nconst
		FROM titles_principals
		WHERE tconst IN (
			SELECT tconst
			FROM titles
			WHERE title_type = 'movie'
		)
        AND tconst IN (
        	SELECT tconst
    		FROM ratings
        	GROUP BY average_rating, tconst
            ORDER BY average_rating ASC
        )
        GROUP BY nconst
		ORDER BY count(nconst) DESC
		LIMIT 1
	);
	
--3b. Geef een overzicht van personen die meer dan 1 functie vervulden bij een film. Dwz èn schrijver èn regisseur waren, of èn acteur èn producer, etc.
--Getest en werkt
--Zonder limit krijg je 1,6 miljoen rows terug, maar dat duurt 30 seconden ipv 1 en ik zou niet weten hoe je al die data zou moeten afbeelden
--Lijkt me wel heel veel data om via zo'n bot te krijgen.
	SELECT primary_name
	FROM names
	WHERE primary_profession LIKE '%,%'
	ORDER BY nconst ASC
	LIMIT 1000
	
--4. In welke films speelde Joop Braakhekke?
--Getest en werkt
	SELECT primary_title 
	FROM titles
	WHERE tconst IN (
		SELECT tconst 
		FROM actors_titles
		WHERE nconst IN (
			SELECT nconst 
			FROM names
			WHERE primary_name = 'Joop Braakhekke'
		)
	);

--5. Welke films van Johnny Depp hebben een 7.5 of hoger?
--Getest en werkt
	--Met code
	SELECT primary_title
	FROM titles
	WHERE title_type = 'movie' AND tconst IN (
		SELECT tconst 
		FROM titles_principals
		WHERE nconst LIKE '%nm0000136%'
	)
	AND tconst IN (
		SELECT tconst
		FROM ratings
		WHERE average_rating >= 7.5
	);
	--Met naam
	SELECT primary_title
	FROM titles
	WHERE title_type = 'movie' AND tconst IN (
		SELECT tconst 
		FROM titles_principals
		WHERE nconst IN (
			SELECT nconst
			FROM names
			WHERE primary_name = 'Johnny Depp'
		)	
	)
	AND tconst IN (
		SELECT tconst
		FROM ratings
		WHERE average_rating >= 7.5
	);

--6. In hoeveel Fast And The Furious films speelde Paul Walker?
--Getest en werkt
	--Met code
	SELECT COUNT(tconst)
	FROM titles
	WHERE title_type = 'movie' AND (primary_title LIKE '%Fast%' OR primary_title LIKE '%Furious%') AND tconst IN (
		SELECT tconst
		FROM titles_principals
		WHERE nconst LIKE '%nm0908094%'
	);
	--Met naam
	--Er zijn 48 Paul Walkers, door het geboortejaar wordt de goede gepakt.
	SELECT COUNT(tconst)
	FROM titles
	WHERE title_type = 'movie' AND (primary_title LIKE '%Fast%' OR primary_title LIKE '%Furious%') AND tconst IN (
		SELECT tconst
		FROM titles_principals
		WHERE nconst IN (
			SELECT nconst
			FROM names
			WHERE primary_name = 'Paul Walker' AND birth_year = '1973' 
		)
	);
	
--7. Welke film heeft de hoogste score met de minste stemmen?
--Getest en werkt
--De bovenste querie is onlangs toegevoegd, maar deze geeft een 9.3 met 19000000 stemmen, en de onderste querie een 10 met 5 stemmen. 
--De onderste lijkt mij dus een beter antwoord geven dus ik laat ze beide eerst even staan.
	SELECT primary_title
	FROM titles
	WHERE title_type = 'movie' AND tconst IN (
		SELECT tconst
		FROM ratings
		WHERE (average_rating * num_votes) IN (
			SELECT max(average_rating * num_votes)
			FROM ratings
		)
	)

	SELECT primary_title 
	FROM titles
	WHERE tconst IN (
		SELECT tconst
		FROM ratings
		WHERE tconst IN (
			SELECT tconst
			FROM titles
			WHERE title_type = 'movie'
			)
		ORDER BY num_votes ASC, average_rating DESC
		LIMIT 1
	);
	
--8. Maak een kaart (b.v. google maps / openstreetview) met landen waar een film speelt. Zodat op de kaart te zien is waar de films spelen. 
--Getest en werkt
	SELECT title, region
	FROM akas
	WHERE types IS NULL AND is_original_title = false AND region IS NOT NULL AND title IN (
		SELECT original_title
		FROM titles
		WHERE title_type = 'movie'
	);
	
--8b. Sommige films hebben een getal in hun titel (bijvoorbeeld: The Sixth Sense, Eight Days a week, Se7en). Maak een visuele weergave van het aantal malen dat een 
--bepaald getal in een filmtitel voorkomt. Bonus: kijk of de Wet van Benford geldt.
--Getest en werkt.
	SELECT sum(case when (lower(primary_title) LIKE '%1%' OR lower(primary_title) LIKE '%one%' OR lower(primary_title) LIKE '%first%') then 1 end) AS no1,
	sum(case when (lower(primary_title) LIKE '%2%' OR lower(primary_title) LIKE '%two%' OR lower(primary_title) LIKE '%second') then 1 end) AS no2,
	sum(case when (lower(primary_title) LIKE '%3%' OR lower(primary_title) LIKE '%three%' OR lower(primary_title) LIKE '%third') then 1 end) AS no3,
	sum(case when (lower(primary_title) LIKE '%4%' OR lower(primary_title) LIKE '%four%') then 1 end) AS no4,
	sum(case when (lower(primary_title) LIKE '%5%' OR lower(primary_title) LIKE '%five%' OR lower(primary_title) LIKE '%fifth') then 1 end) AS no5,
	sum(case when (lower(primary_title) LIKE '%6%' OR lower(primary_title) LIKE '%six%') then 1 end) AS no6,
	sum(case when (lower(primary_title) LIKE '%7%' OR lower(primary_title) LIKE '%seven%') then 1 end) AS no7,
	sum(case when (lower(primary_title) LIKE '%8%' OR lower(primary_title) LIKE '%eight') then 1 end) AS no8,
	sum(case when (lower(primary_title) LIKE '%9%' OR lower(primary_title) LIKE '%nine%' OR lower(primary_title) LIKE '%ninth') then 1 end) AS no9,
	sum(case when (lower(primary_title) LIKE '%0%' OR lower(primary_title) LIKE '%zero%') then 1 end) AS no0
	FROM titles
	WHERE title_type = 'movie'
	
--9. Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films
--Getest en werkt	
	SELECT count(original_title), start_year AS syear
	FROM titles
	WHERE start_year <= date_part('year', CURRENT_DATE) AND title_type = 'movie' AND original_title IN (
		SELECT title
		FROM akas
		WHERE types IS NULL AND is_original_title = false AND region = '(voer hier regio-code in)'
	)
	GROUP BY syear
	ORDER BY syear ASC
	
	--Voor de aantallen in ranges
	SELECT count(original_title)
	FROM titles
	WHERE start_year BETWEEN 1900 AND 1920 AND title_type = 'movie' AND original_title IN (
		SELECT title
		FROM akas
		WHERE types IS NULL AND is_original_title = false AND region = 'NL'
	)
		
--Random film
	SELECT primary_title
	FROM titles
	WHERE title_type = 'movie' AND tconst in (
		SELECT tconst 
		FROM ratings
		WHERE average_rating >= 6
	)
	ORDER BY RANDOM()
	LIMIT 1
	
--Random serie
	SELECT primary_title
	FROM titles
	WHERE title_type = 'tvSeries' AND tconst in (
		SELECT tconst 
		FROM ratings
		WHERE average_rating >= 7
	)
	ORDER BY RANDOM()
	LIMIT 1


	
	
	
	