# Spring Boot AI to SVG COnverter
This app is used to convert .ai files to .svg files with user interface .

Article link : https://github.com/sreedharrao225/AI2SVGConverter
```
#Prerequisites
install mvn
install git
Add below system environment variables for App(below env variables are optional)
1)API_KEY (api key from cloudconverter)
2)FILE_STORAGE_PATH (path where you wanted to temp store the source file ,files get deleted once processed)
3)FILE_STORAGE_OUTPUT_PATH(output path for converted files where files get store on yuor disk)
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
