
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
    { $out : "combined_sic_activities"}
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
    { $merge : "combined_sic_activities"}
]);

db.combined_sic_activities.createIndex( { "sic_code" : 1 } )
