#!/bin/bash 

#
# Simple script to recreate your local Mongo Database
#
# The datafiles have fields in the first line
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

import_csv() {
    local csv_filename=$1
    local mongo_collection=$2

    local csv_file_full_path=${script_dir}/../datafiles/${csv_filename}
    if [[ ! -f "${csv_file_full_path}" ]]; then  
        echo "CSV Input file ${csv_file_full_path} is not found, exiting script"
        exit 1
    fi 

    echo "Importing the CSV to Collection ${mongo_collection} with drop option used"
    mongoimport --uri="${SIC_CODE_API_MONGO_URL}" --db "${SIC_CODE_API_DATABASE}" --collection "${mongo_collection}" --file "${csv_file_full_path}" --drop --type=csv --headerline --columnsHaveTypes
}

create_combined_sic_activites() {

    local javascript_file=create_combined_sic_activites.js
    local javascript_file_full_path=${script_dir}/${javascript_file}

    if [[ ! -f "${javascript_file_full_path}" ]]; then  
        echo "JavaScript file ${javascript_file_full_path} is not found, exiting script"
        exit 1
    fi 

    echo "Creating new combined collection in ${SIC_CODE_API_DATABASE} database"
    mongo "${SIC_CODE_API_MONGO_URL}/${SIC_CODE_API_DATABASE}" "${javascript_file_full_path}"

}

import_csv SIC07_CH_condensed_list_en.csv condensed_sic_codes 

import_csv ch_created_activity_sic_codes.csv ch_economic_activity_sic_codes 

import_csv uksic2007_activities_alphabetic_index_november2020.csv ons_economic_activity_sic_codes

create_combined_sic_activites

echo "Finished OK"