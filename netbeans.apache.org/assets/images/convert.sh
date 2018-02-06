#!/bin/sh
convert -background none -density 1200 -resize x64 apache-netbeansx1024.png nblogo64x64.png
convert -background none -density 1200 -resize x32 apache-netbeansx1024.png nblogo32x32.png
convert -background none -density 1200 -resize x96 apache-netbeansx1024.png nblogo96x96.png
convert -background none -density 1200 -resize x152 apache-netbeansx1024.png touch-icon-ipad.png
convert -background none -density 1200 -resize x180 apache-netbeansx1024.png touch-icon-iphone-retina.png
convert -background none -density 1200 -resize x167 apache-netbeansx1024.png touch-icon-ipad-retina.png

