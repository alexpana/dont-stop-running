#!/bin/bash

if [ -a core/assets ]
then
	echo "Updating assets"
	pushd
	cd core/assets
	git fetch
	popd
else
	echo "Fetching assets repo"
	git clone https://bitbucket.org/alex_pana/dont-stop-running-assets core/assets
fi