URL="http://localhost:8080/continents?countries=CA,HK"
for i in {1..10}
do
  if [ "$i" -lt 10 ]
  then
    printf "0"
  fi
  printf "%s: calling URL, status: " "$i"
  curl -s -o /dev/null -I -w "%{http_code}" "$URL"
  echo ""
done
echo "wait 1 s"
sleep 1
for i in {1..10}
do
  if [ "$i" -lt 10 ]
    then
      printf "0"
    fi
  printf "%s: calling URL, status: " "$i"
  curl -s -o /dev/null -I -w "%{http_code}" "$URL"
  echo ""
done
