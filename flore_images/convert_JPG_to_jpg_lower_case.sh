#!/bin/bash

#simulation du renommage si OK, mettre -v à la place de -n
find . -name "*.JPG" -exec rename -v 's/.JPG/.jpg/' {} \;

