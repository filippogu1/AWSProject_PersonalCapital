import csv
import json

# python -c "import csv_to_json as converter; converter.csv_2_json();"
def csv_2_json():
    with open('F:\\Personal Capital\\F_5500_2017_Latest\\f_5500_2017_latest.csv', 'r') as infile:
        creader = csv.DictReader(infile)
        with open('F:\\Personal Capital\\F_5500_2017_Latest\\f_5500_2017_latest.json\\f_5500_2017_latest_2.json', 'w') as outfile:
            i = 1
            for row in creader:
                out = json.dumps(row)
                outfile.write(out)
                outfile.write('\n')
                if i % 1000 == 0:
                    print('dumped {} line'.format(i))
                i += 1