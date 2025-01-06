package com.example.mongobatch.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface BatchTestMongoRepository : MongoRepository<BatchTestDocument, String>
