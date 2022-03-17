/*
  This mongo script will recreate the combined_sic_activities collection from:
  - ons_economic_activity_sic_codes, AND
  - ch_economic_activity_sic_codes

  Adding additional fields to help the search 
  - sic_description (from condensed_sic_codes)
  - is_ch_activity (true if parent is ch_economic_activity_sic_codes)
  - activity_description_lower_case (lower case of the activity_description field)


*/

db.combined_sic_activities.drop()

db.ons_economic_activity_sic_codes.aggregate([
    { $lookup:
        {
           from: "condensed_sic_codes",
           localField: "sic_code",
           foreignField: "sic_code",
           as: "sicRecord"
        }
    },
    {
        $unwind:"$sicRecord"
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_lower_case" : { $toLower: "$activity_description"},
            "sic_description" : "$sicRecord.sic_description",
            "is_ch_activity" : {$toBool: false}
        }
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_lower_case" : 1,
            "activity_description_search_field_a" : { $replaceAll: { input: "$activity_description_lower_case" , find: "(", replacement: "" } }            ,
            "sic_description" : 1,
            "is_ch_activity" : 1
        }
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_search_field" : { $replaceAll: { input: "$activity_description_search_field_a" , find: ")", replacement: "" } }            ,
            "sic_description" : 1,
            "is_ch_activity" : 1
        }
    },
    { $out : "combined_sic_activities"}
]);

db.solarSystem.aggregate([
    { "$project": { "_id": 0, "name": 1, "gravity.value": 1} },
    { "$project": { "gravity": "$gravity.value" }}
]);

db.ch_economic_activity_sic_codes.aggregate([
    { $lookup:
        {
           from: "condensed_sic_codes",
           localField: "sic_code",
           foreignField: "sic_code",
           as: "sicRecord"
        }
    },
    {
        $unwind:"$sicRecord"
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_lower_case" : { $toLower: "$activity_description"},
            "sic_description" : "$sicRecord.sic_description",
            "is_ch_activity" : {$toBool: true}
        }
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_lower_case" : 1,
            "activity_description_search_field_a" : { $replaceAll: { input: "$activity_description_lower_case" , find: "(", replacement: "" } }            ,
            "sic_description" : 1,
            "is_ch_activity" : 1
        }
    },
    {
        $project:{
            "_id":1,
            "sic_code" : 1,
            "activity_description" : 1,
            "activity_description_search_field" : { $replaceAll: { input: "$activity_description_search_field_a" , find: ")", replacement: "" } }            ,
            "sic_description" : 1,
            "is_ch_activity" : 1
        }
    },
    { $merge : "combined_sic_activities"}
]);
