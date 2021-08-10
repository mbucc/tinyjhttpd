#! /bin/sh -e

javac -d mods --module-source-path . $(find . -name *.java)
