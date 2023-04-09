:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT CST8221 - Winter 2023
:: ---------------------------------------------------------------------
:: Begin of Script (A13 - W23)
:: ---------------------------------------------------------------------

CLS

:: LOCAL VARIABLES ....................................................

SET SRCDIR=src
SET BINDIR=bin
SET BINERR=a32-javac.err
SET JARNAME=a32.jar
SET JAROUT=a32-jar.out
SET JARERR=a32-jar.err
SET DOCDIR=doc
SET DOCPACK=picross
SET DOCERR=a32-javadoc.err
SET MAINCLASSSRC=src/picross/Main.java
SET MAINCLASSBIN=picross.Main

@echo off

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                                                                   @"
ECHO "@                   #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@                  ##       @  A L G O N Q U I N  C O L L E G E  @  @"
ECHO "@                ##  #      @    JAVA APPLICATION PROGRAMMING    @  @"
ECHO "@             ###    ##     @        W I N T E R - 2 0 2 3       @  @"
ECHO "@          ###    ##        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@        ###    ##                                                  @"
ECHO "@        ##    ###                 ###                              @"
ECHO "@         ##    ###                ###                              @"
ECHO "@           ##    ##               ###   #####  ##     ##  #####    @"
ECHO "@         (     (      ((((()      ###       ## ###   ###      ##   @"
ECHO "@     ((((     ((((((((     ()     ###   ######  ###  ##   ######   @"
ECHO "@        ((                ()      ###  ##   ##   ## ##   ##   ##   @"
ECHO "@         ((((((((((( ((()         ###   ######    ###     ######   @"
ECHO "@         ((         ((           ###                               @"
ECHO "@          (((((((((((                                              @"
ECHO "@   (((                      ((                                     @"
ECHO "@    ((((((((((((((((((((() ))                                      @"
ECHO "@         ((((((((((((((((()                                        @"
ECHO "@                                                                   @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"

ECHO "[LABS SCRIPT ---------------------]"

ECHO "1. Compiling ......................"
::javac -Xlint -cp ".;src;/SOFT/copy/dev/java/javafx/lib/*;/SOFT/COPY/db/derby/lib/*" src/Lab.java -d bin 2> labs-javac.err
javac -Xlint -cp ".;%SRCDIR%;" %MAINCLASSSRC% -d %BINDIR% 2> %BINERR%

:: ECHO "Running  ........................."
:: start java -cp ".;bin;/SOFT/copy/dev/java/javafx/lib/*" CST8221.Main

ECHO "2. Creating Jar ..................."
cd bin
::jar cvfe CST8221.jar Lab . > labs-jar.out 2> labs-jar.err
jar cvfe %JARNAME% %MAINCLASSBIN% . > ../%JAROUT% 2> ../%JARERR%

ECHO "3. Creating Javadoc ..............."
cd ..
::javadoc -cp ".;bin;/SOFT/copy/dev/java/javafx/lib/*;/SOFT/COPY/db/derby/lib/*;/SOFT/COPY/dev/LIBS/jar/javax.servlet.jar" --module-path "C:\SOFT\COPY\dev\LIBS\javafx\lib" --add-modules javafx.controls -d doc -sourcepath src -subpackages CST8221 2> labs-javadoc.err
javadoc -cp ".;%BINDIR%;" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK% 2>%DOCERR%

cd bin
ECHO "4. Running Jar ...................."
::start java --module-path "/SOFT/COPY/dev/LIBS/javafx/lib;/SOFT/COPY/db/derby/lib" --add-modules javafx.controls,javafx.fxml -jar CST8221.jar
start java -jar %JARNAME%
cd ..

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

:: ---------------------------------------------------------------------
:: End of Script (A13 - W23)
:: ---------------------------------------------------------------------
