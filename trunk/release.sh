cp dist/DSHub.jar . 
rm -rf lib
rm -rf javadoc
cp -rf dist/javadoc .
cp -rf dist/lib .
find *.jar *.txt readme.txt license.txt LICENSE.slf4j.txt mina-license.txt logo.jpg LICENSE.jzlib.txt changelog.txt lib modules/*.jar DSHub.jar\
 -wholename "*/.svn" -prune -o -print | grep -v gch$ | zip -9 DSHub-$1-bin.zip -9 -@
find *.java *.form *.jpg *.xml *.properties *.jar *.sh *.html *.css *.gif readme.txt license.txt LICENSE.slf4j.txt mina-license.txt LICENSE.jzlib.txt changelog.txt nbproject src lib modules javadoc manifest.mf build.xml \
 -wholename "*/.svn" -prune -o -print | grep -v gch$ | zip -9 DSHub-$1-src.zip -9 -@
zip -9 DSHub-$1-src.zip javadoc