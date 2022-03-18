#!/bin/bash 

#
# Simple script to recreate your local Mongo Database using the json files created by the export-mongo-collections.sh script
#
  
set -o errexit # abort on nonzero exitstatus
set -o pipefail  # don't hide errors within pipes


if [[ -z "${SIC_CODE_API_DATABASE}" ]]; then
    echo "ERROR: SIC_CODE_API_DATABASE environmental variable NOT set"
    exit 1
fi

if [[ -z "${SIC_CODE_API_MONGO_URL}" ]]; then
    echo "ERROR: SIC_CODE_API_MONGO_URL environmental variable NOT set"
    exit 1
fi

script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

import_json() {
    local json_filename=$1

    local json_file_full_path=${script_dir}/../import_files/${json_filename}
    if [[ ! -f "${json_file_full_path}" ]]; then  
        echo "JSON Input file ${json_file_full_path} is not found, exiting script"
        exit 1
    fi 

    echo "Importing the JSON to Collection from file ${json_filename} with drop option used"
    mongoimport --uri="${SIC_CODE_API_MONGO_URL}" --db "${SIC_CODE_API_DATABASE}" --file "${json_file_full_path}" --drop --jsonArray
}

import_json ch_economic_activity_sic_codes.json

import_json combined_sic_activities.json

import_json condensed_sic_codes.json

import_json ons_economic_activity_sic_codes.json

echo "Finished OK"
