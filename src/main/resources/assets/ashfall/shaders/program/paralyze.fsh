#version 150
//Paralysis Fragment


uniform sampler2D DiffuseSampler;
uniform sampler2D BlurSampler; //--

uniform vec4 ColorModulate;
uniform int Multiplier;// just checking ints work
uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec2 BlurDir;
//uniform float Radius;


in vec2 oneTexel;
in vec2 texCoord;

out vec4 fragColor;


vec4 blurSwatch() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    for(float r = -5; r <= 5; r += 1.0) {
        vec4 sampleValue = texture(DiffuseSampler, texCoord + oneTexel * r * BlurDir);

        // Accumulate average alpha
        totalAlpha = totalAlpha + sampleValue.a;
        totalSamples = totalSamples + 1.0;

        // Accumulate smoothed blur
        float strength = 1.0 - abs(r / 5);
        totalStrength = totalStrength + strength;
        blurred = blurred + sampleValue;
    }
    float gs = (blurred.x + blurred.y + blurred.z)/3;
    blurred.rgb = vec3(gs);
    return vec4(blurred.rgb / (5 * 2.0 + 1.0), totalAlpha);
}

void main() {

    //references
    //vec2 trueCoord = gl_FragCoord.xy / OutSize.x;
    //float proportion = OutSize.x / OutSize.y;
    //vec2 mid = vec2(0.5, 0.5 / prop);

    //define norm
    fragColor = texture(DiffuseSampler, texCoord) * ColorModulate * float(Multiplier);
    //blur additive
    //vec4 blurColor = texture(BlurSampler, texCoord) * ColorModulate * float(Multiplier);
    //fragColor.xyz = mix(fragColor.xyz, blurColor.xyz,distance(texCoord,vec2(0.5)));
    //greyscaling calculator
    //float gs = (fragColor.x + fragColor.y + fragColor.z)/3;
    //apply greying at edges
    //if(texCoord.y > 0.5){
    fragColor.xyz = mix(fragColor.xyz, blurSwatch().xyz, distance(texCoord,vec2(0.5)));
    //}

}


