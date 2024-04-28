package com.cafeconpalito.chikara.domain.model

data class ChickDto (

        val id:String?,
        val title : String,
        val author : String?,
        val date : String?,
        val likes : Long,
        val isprivate : Boolean,
        val content : List<ChickContentDto>,
        val comments : List<ChickCommentDto>,
        val mencions : List<String>

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

)