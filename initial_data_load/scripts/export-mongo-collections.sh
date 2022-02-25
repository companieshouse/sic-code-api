#!/bin/bash

#
# Simple script to export the sic_code collections
#

set -o errexit  # abort on nonzero exitstatus
set -o pipefail # don't hide errors within pipes

if [[ -z "${SIC_CODE_API_DATABASE}" ]]; then
    echo "ERROR: SIC_CODE_API_DATABASE environmental variable NOT set"
    exit 1
fi

if [[ -z "${SIC_CODE_API_MONGO_URL}" ]]; then
    echo "ERROR: SIC_CODE_API_MONGO_URL environmental variable NOT set"
    exit 1
fi

script_dir=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
import_files=${script_dir}/../import_files
echo "Writing import files to ${import_files}"

export_collection() {
    local mongo_collection="$1"
    local output_file="${import_files}/${mongo_collection}.json"

    echo "Exporting collection ${mongo_collection} to json file ${output_file}"
    mongoexport --uri="$SIC_CODE_API_MONGO_URL" --db "${SIC_CODE_API_DATABASE}" --collection "${mongo_collection}" --out="${output_file}"
}

export_collection ch_economic_activity_sic_codes
export_collection combined_sic_activities
export_collection condensed_sic_codes
export_collection ons_economic_activity_sic_codes

echo "Finished OK"
