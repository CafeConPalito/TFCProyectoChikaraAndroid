package com.cafeconpalito.chikara.domain.model

data class ChickDto (

        var _id:String = "",
        val title : String,
        val author : String? = null,
        val date : String? = null,
        var likes : Long = 0,
        val isprivate : Boolean,
        var content : List<ChickContentDto> = emptyList(),
        var comments : List<ChickCommentDto> = emptyList(),
        var mencions : List<String> = emptyList()

)

//    [
//    {
//        "title": "string",
//        "author": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
//        "date": "2024-04-28",
//        "likes": 0,
//        "status": "string",
//        "content": [
//        {
//            "position": 0,
//            "content": "string",
//            "type": "string"
//        }
//        ],
//        "comments": [
//        {
//            "user": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
//            "comment": "string",
//            "date": "2024-04-28"
//        }
//        ],
//        "mencions": [
//        "3fa85f64-5717-4562-b3fc-2c963f66afa6"
//        ]
//    }
//    ]