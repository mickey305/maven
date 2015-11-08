
# repository root
dir=$(cd $(dirname $0); pwd)

cd $dir/../projects/AndroidLibApp
./gradlew assembleDebug
