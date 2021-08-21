# Wordcount

This is a sample project made for Ordina.

The project has been made in spring with a small form interface to interact with.

To manually call the functions go the following endpoints:

# Endpoints

The endpoint for the function **CalculateHighestFrequency**

http://localhost:8080/api/frequency/?text= {your sentence}

**Example: **

 http://localhost:8080/api/frequency/?text=The%20sun%20shines%20over%20the%20lake
<hr />  

The endpoint for the function **CalculateFrequencyForWord**

http://localhost:8080/api/word/?text= {your sentence}&word={your word}  

**Example: **

http://localhost:8080/api/word/?text=The%20sun%20shines%20over%20the%20lake&word=the
<hr />

The endpoint for the function **CalculateFrequencyForWord**

http://localhost:8080/api/frequencies/ {n}?text={your sentence}

**Example: **

http://localhost:8080/api/frequencies/3?text=The%20sun%20shines%20over%20the%20lake
  
