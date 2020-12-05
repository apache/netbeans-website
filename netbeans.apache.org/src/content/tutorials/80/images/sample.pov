#include "colors.inc"
#include "textures.inc"
#include "shapes.inc"
#include "metals.inc"
#include "glass.inc"
#include "woods.inc"

global_settings {max_trace_level 1000}

camera {
//Some other interesting angles to look at the cube from in comments:
   location <-22, 16, -25>

//   location <-0.1, 0.3, -0.4>
//   location <-1, 3, -4>
//   location <-2, 6, -5>
//   location <0, 0, -35>

//If we want an animation:
//   location <clock * -22, clock * 16, clock * -25>

   direction <0, 0,  2.25>
   right x*1.33
   look_at <0,0,0>
}


 light_source {< -50, 25, -50> color rgb <1.0, 1.0, 1.0>
//Some parameters to play with in comments:

//    fade_distance Dist fade_power 2
//   area_light <-40, 0, -40>, <40, 0, 40>, 3, 3
//   adaptive 1
//   jitter
}

#declare LogoColor =
    pigment {
        color rgb <1.0, 0.0, 0.0>
    }

#declare LogoTexture =
        texture {
            LogoColor
            finish { specular 0.65 roughness 0.1 ambient 0.3 reflection 0.55 }
            translate x*1
            rotate <15, 10, 0>
            translate y*2
        }

#declare LEG_DIAMETER = 0.35;

#declare Leg =
union {

        sphere { <1.5, 1.5, -0.75>, LEG_DIAMETER
                texture { LogoTexture }
        }

        cylinder { <1.5, 1.5, -0.75>, <-1.5, 1.5, -0.75>, LEG_DIAMETER
                texture { LogoTexture }
        }

        sphere { <-1.5, 1.5, -0.75>, LEG_DIAMETER
                texture { LogoTexture }
        }
}

#declare DotLeg =
union {
        object { Leg }
        sphere { <2 + LEG_DIAMETER, 1.5, -0.75>, LEG_DIAMETER
                texture { LogoTexture }
                }
        sphere { <-2 + -LEG_DIAMETER, 1.5, -0.75>, LEG_DIAMETER
                texture { LogoTexture }
                }
        }

#declare VertLeg =
        object {
            Leg
            rotate <0,0,90>
        }

#declare HorizBox =
union {
        object {
           DotLeg
           translate <0, 0, 0.5>
       }
       object {
           Leg
           rotate <0, 90, 0>
           translate <-1.05 + -LEG_DIAMETER + -LEG_DIAMETER/2, 0, 2.1>
       }

       object {
           Leg
           rotate <0, 90, 0>
           translate <3.1, 0, 2.1>
           }
       object {
           DotLeg
           translate <0, 0, 5.17>
      }
}

#declare Logo =
union {

        object {
                HorizBox
                translate y*LEG_DIAMETER + y*LEG_DIAMETER
                translate y*-5.17
                }

        object {
                HorizBox
                translate y*0
                }

        object {
                VertLeg
                   translate <-LEG_DIAMETER*2, -LEG_DIAMETER + -LEG_DIAMETER, LEG_DIAMETER + LEG_DIAMETER/2>
                }


        object {
                VertLeg
                   translate <-LEG_DIAMETER*2, -LEG_DIAMETER + -LEG_DIAMETER , 5.17>
                }


         object {
                VertLeg
                   translate <3.8, -LEG_DIAMETER + -LEG_DIAMETER, LEG_DIAMETER + LEG_DIAMETER/2>
                }


        object {
                VertLeg
                   translate <3.8, -LEG_DIAMETER + -LEG_DIAMETER , 5.17>
                }
        }

#declare OTHERS =
union {
object {
        Logo
        translate <-10, 0, 0>
        }

object {
        Logo
        translate <-10, -10, 0>
        }

object {
        Logo
        translate <-10, -10, -10>
        }

object {
        Logo
        translate <10, 0, 0>
        }

object {
        Logo
        translate <10, -10, 0>
        }

object {
        Logo
        translate <10, 10, 10>
        }
        }


 object {
        Logo
        }
