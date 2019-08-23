# Spring Boot AI to SVG COnverter
This app is used to convert .ai files to .svg files with user interface .

Article link : https://github.com/sreedharrao225/AI2SVGConverter
```
#Prerequisites
Java 8
install mvn
install git
Add below system environment variables for App
1)API_KEY ( Mandaotry ,Api key from cloudconverter for authentication)
2)FILE_STORAGE_PATH ( Optional ,path where you wanted to temp store the source file ,files get deleted once processed)
3)FILE_STORAGE_OUTPUT_PATH( Optional , output path for converted files where files get store on yuor disk)
```
## 1. How to start
```
$ git clone https://github.com/sreedharrao225/AI2SVGConverter.git
$ cd AI2SVGConverter
$ mvn clean
$ mvn spring-boot:run
Application will start after above cmd and you can access home page at http://localhost:8080/
browse .ai file and click on convert to process and download the .svg file 

```
