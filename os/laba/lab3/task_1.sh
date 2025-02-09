#!/bin/bash

mkdir ~/test && echo "catalog test was created successfully" >> ~/report && touch ~/test/$(date | tr "[:space:]" "_")
ping -c1 www.net_nikogo.ru || echo "$(date) - error host" >> ~/report
