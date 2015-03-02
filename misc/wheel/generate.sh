#!/bin/bash

ORIGINAL_VALUE=48
FLAG="-w"

function export {
  mkdir -p $2
  VALUE=$(echo "scale=0; $ORIGINAL_VALUE*$1" | bc -l)
  CMD="inkscape $FLAG$VALUE --export-background-opacity=0 --export-png=$2/$3 temp.svg > /dev/null"
  eval $CMD
}

for NUMBER in 0 1 2 3 4 5 6 7 8 9
do
    for ANGLE in 88 85 80 70 60 50 40 30 20 10 5 2 0
    do
        COMMAND="./roue.php $NUMBER $ANGLE"
        FILENAME=${NUMBER}-${ANGLE}.png
        $COMMAND > temp.svg
        export 1 drawable-mdpi $FILENAME
        export 1.5 drawable-hdpi $FILENAME
        export 2 drawable-xhdpi $FILENAME
        export 3 drawable-xxhdpi $FILENAME
        export 4 drawable-xxxhdpi $FILENAME
        rm temp.svg
        echo " - $FILENAME"
    done
    echo num_${NUMBER}.gif
done
cp -r drawable-* ../../app/src/main/res/.
rm -r drawable-*
