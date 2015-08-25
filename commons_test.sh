
# repository root
dir=$(cd $(dirname $0); pwd)

cd $dir/projects/common-library
./gradlew clean build uploadArchives
