#version 150
//Wardentouch Fragment

//Goal:180
//tolerance:10 - more blue, to beginning teal

uniform sampler2D DiffuseSampler;
uniform vec4 ColorModulate;
uniform int Multiplier;// just checking ints work
uniform vec2 InSize;
uniform vec2 OutSize;



in vec2 oneTexel;
in vec2 texCoord;

out vec4 fragColor;

//UTILS
//-------------------
vec3 rgb2hsv(vec3 intake){
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(intake.bg, K.wz), vec4(intake.gb, K.xy), step(intake.b, intake.g));
    vec4 q = mix(vec4(p.xyw, intake.r), vec4(intake.r, p.yzx), step(p.x, intake.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}
vec3 hsv2rgb(vec3 outtake){
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(outtake.xxx + K.xyz) * 6.0 - K.www);
    return outtake.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), outtake.y);
}

//-------------------


void main() {
    //define norms
    fragColor = texture(DiffuseSampler, texCoord) * ColorModulate * float(Multiplier);
    vec3 hsv = rgb2hsv(fragColor.xyz);
    float hue = rgb2hsv(fragColor.xyz).x * 360;

    //check for teal and bright blue, between 170 and 190;
    float g = 180.0;
    float w = 10;
    float s = (hue < mod(g+w, 360.0) && hue > mod(g-w, 360.0)) ? 1 : 0;
    vec3 e = hsv2rgb(vec3(hue/360, hsv.y * s, hsv.z)).rgb;

    //greyscaling calculator for reference
    vec3 gs = vec3((fragColor.x + fragColor.y + fragColor.z)/3);
    fragColor.xyz = mix(fragColor.xyz,e, 0.9*distance(texCoord,vec2(0.5)));
    //fragColor.xyz = e;


}


