# Spring Boot .AI to .SVG Converter
This app is used to convert .ai files to .svg files with user interface .This app servers as java client for cloudConvert and can be extended for any file conversion types.

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

Home page looks like below

![image](https://user-images.githubusercontent.com/29558315/63591665-6950d600-c57d-11e9-9c9d-5579d4441352.png)
