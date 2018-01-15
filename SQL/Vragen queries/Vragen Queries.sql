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
			ORDER BY COUNT(tconst) DESC
			LIMIT 1
		)
	);	
		
--3. Welke schrijvers spelen in hun eigen films en welke films zijn dat? 
	SELECT c.nconst, t.primary_title
	FROM titles AS t, titles_crew AS c
	WHERE c.tconst IN (
		SELECT p.tconst
		FROM titles_principals AS p, titles_crew AS c
		WHERE c.tconst = p.tconst AND c.role = 'writer'
	);

--4. Welke acteur of actrice speelt het meest in de slechtst gewaardeerde films?
	SELECT primary_name 
	FROM names 
	WHERE nconst IN (
        SELECT nconst
        FROM titles
        WHERE title_type = 'movie' AND tconst IN (
            SELECT tconst
            FROM ratings 
            WHERE 
		)
	);

--5. Welke films van Johnny Depp hebben een 7.5 of hoger?
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
	SELECT titles.primary_title, akas.region
	FROM titles, akas
	WHERE titles.original_title IN (
		SELECT title
		FROM akas
		WHERE types IS NULL AND is_original_title = false AND region IS NOT NULL
	);
	
--9. Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films
--Getest en werkt	
	SELECT count(original_title), start_year 
	FROM titles
	WHERE start_year <= 2018 AND original_title IN (
		SELECT title
		FROM akas
		WHERE types IS NULL AND is_original_title = false AND region = '(voer hier regio-code in)'
	)
	GROUP BY start_year
	
	
	
	