package com.example.nasamaterial.dto

data class NasaMarsDTO(
    val photos: List<PhotosMarsDTO?>
)

data class PhotosMarsDTO(
    val id: Int?,
    val sol: Int?,
    val camera: CameraMarsDTO?,
    val img_src: String?,
    val earth_date: String?,
    val rover: RoverMarsDTO?
)

data class CameraMarsDTO(
    val id: Int?,
    val name: String?,
    val rover_id: Int?,
    val full_name: String?
)

data class RoverMarsDTO(
    val id: Int?,
    val name: String?,
    val landing_date: String?,
    val launch_date: String?,
    val status: String?
)
