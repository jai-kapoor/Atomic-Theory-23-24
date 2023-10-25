package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Gamepad;


public class MecanumRobot {
    // --------------------------------------
    // Declare motors

    public static final double THETA_TOLERANCE = 0.04;
    public static double XY_TOLERANCE = 0.05;
    DcMotor flMotor, frMotor, blMotor, brMotor, intakemotor, liftmotor;
    Servo launchservo = null, boxServo = null;
    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.937 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    LinearOpMode opMode;

//    private BNO055IMU imu = null;


    void init(HardwareMap hardwareMap, LinearOpMode opMode) {
        this.opMode = opMode;

        //imu
//        imu = hardwareMap.get(BNO055IMU.class, "imu 1");
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.mode = BNO055IMU.SensorMode.IMU;
//        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
//        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.loggingEnabled = false;
//        imu.initialize(parameters);

        flMotor = hardwareMap.get(DcMotor.class, "fl");
        frMotor = hardwareMap.get(DcMotor.class, "fr");
        blMotor = hardwareMap.get(DcMotor.class, "bl");
        brMotor = hardwareMap.get(DcMotor.class, "br");
        intakemotor = hardwareMap.get(DcMotor.class, "intake");
        liftmotor = hardwareMap.get(DcMotor.class, "lift");
        //launchservo = hardwareMap.get(Servo.class, "launcher");
        boxServo = hardwareMap.get(Servo.class, "boxServo");


        // Set motor directions
        flMotor.setDirection(DcMotor.Direction.FORWARD);
        frMotor.setDirection(DcMotor.Direction.REVERSE);
        blMotor.setDirection(DcMotor.Direction.FORWARD);
        brMotor.setDirection(DcMotor.Direction.REVERSE);
        intakemotor.setDirection(DcMotor.Direction.REVERSE);
        liftmotor.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to brake when power is zero
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakemotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

//    void resetEncoder(DcMotor motor) {
//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//    }

    void drive(double x_stick, double y_stick, double x_right_stick, double multiplier) {
        if (Math.abs(x_stick) >= (2 * Math.abs(y_stick)) + .1) {
            flMotor.setPower(x_stick * multiplier);
            frMotor.setPower(-x_stick * multiplier);
            blMotor.setPower(-x_stick * multiplier);
            brMotor.setPower(x_stick * multiplier);
        } else {
            flMotor.setPower((y_stick + x_right_stick) * multiplier);
            frMotor.setPower((y_stick - x_right_stick) * multiplier);
            blMotor.setPower((y_stick + x_right_stick) * multiplier);
            brMotor.setPower((y_stick - x_right_stick) * multiplier);
        }
    }

    /**
     * Stop the drive motors
     */

//    public void run(Gamepad gamepad) {
//        //Get the positions of the left stick in terms of x and y
//        //Invert y because of the input from the controller
//        double stickX = Math.abs(gamepad.left_stick_x) < Constants.STICK_THRESH ? 0 : gamepad.left_stick_x;
//        double stickY = Math.abs(gamepad.left_stick_y) < Constants.STICK_THRESH ? 0 : -gamepad.left_stick_y;
//        //get the direction from the IMU
//        double angle = imu.getAngularOrientation().firstAngle;
//        //rotate the positions to prep for wheel powers
//        double rotatedX = (stickX * Math.cos(PI / 4)) - (stickY * Math.sin(PI / 4));
//        double rotatedY = (stickY * Math.cos(PI / 4)) + (stickX * Math.sin(PI / 4));
//        //determine how much the robot should turn
//        double rotation = (gamepad.left_trigger - gamepad.right_trigger) * Constants.ROTATION_SENSITIVITY;
//        //test if the robot should move
//        double stickPower = Math.sqrt(rotatedX * rotatedX + rotatedY * rotatedY);
//        boolean areTriggersDown = Math.abs(rotation) > 0;
//        boolean areSticksMoved = stickPower > 0;
//        if (areSticksMoved || areTriggersDown) {
//            //add the rotation to the powers of the wheels
//            double motorMax = Math.max(Math.abs(rotatedX)+rotation, Math.abs(rotatedY)+rotation);
//            double proportion = Math.max(1, motorMax);
//            double num = (1 / proportion)* stickPower;
//            double flPower = num * rotatedY - rotation;
//            double brPower = num * rotatedY/proportion + rotation;
//            double frPower = num * -rotatedX/proportion + rotation;
//            double blPower = num * -rotatedX/proportion - rotation;
//            //keep the powers proportional and within a range of -1 to 1
//
//            flMotor.setPower(flPower / proportion);
//            brMotor.setPower(brPower / proportion);
//            frMotor.setPower(frPower / proportion);
//            blMotor.setPower(blPower / proportion);
//        } else {
//            driveStop();
//        }
//    }
    void driveStop() {
        flMotor.setPower(0);
        frMotor.setPower(0);
        blMotor.setPower(0);
        brMotor.setPower(0);
    }

    void driveForward(double power) {
        flMotor.setPower(-power);
        blMotor.setPower(power);
        frMotor.setPower(-power);
        brMotor.setPower(-power);
    }

    void strafeRight(double power) {
        flMotor.setPower(power);
        blMotor.setPower(-power);
        frMotor.setPower(-power);
        brMotor.setPower(power);
    }

    void turnClockwise(double power) {
        flMotor.setPower(-power);
        blMotor.setPower(power);
        frMotor.setPower(power);
        brMotor.setPower(power);
    }




//    void intakeIn() { intakemotor.setPower(1.0); }
//    void IntakeOut (){
//        intakemotor.setPower(-1.0);
//    }
//
//    void duckzero () {
//        intakemotor.setPower(0);
//    }



    void liftUp(){
        liftmotor.setPower(1.0);
    }

    public void hammerBack(){
        launchservo.setPosition(0.5);
    }

    public void hammerPush(){ launchservo.setPosition(-0.5); }



    void driveForwardByEncoder(double inches, DcMotor motor, double power) {
        power = Math.abs(power);
        double positionChange = inches * COUNTS_PER_INCH;
        int oldPosition = motor.getCurrentPosition();
        double targetPosition = oldPosition + positionChange; // minus not plus, flipped motor

        if (positionChange > 0) {
            driveForward(power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() < targetPosition) {
                opMode.telemetry.addData("auto", motor.getCurrentPosition());
                Thread.yield();
            }
            driveStop();
        } else if (positionChange < 0) {
            driveForward(-power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() > targetPosition) {
                Thread.yield();
            }
            driveStop();
        }



    }




    void liftByEncoder(double inches, DcMotor motor, double power) {
        power = Math.abs(power);
        double positionChange = inches * COUNTS_PER_INCH;
        int oldPosition = motor.getCurrentPosition();
        double targetPosition = oldPosition + positionChange; // minus not plus, flipped motor

        if (positionChange > 0) {
            liftmotor.setPower(power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() < targetPosition) {
                opMode.telemetry.addData("auto", motor.getCurrentPosition());
                Thread.yield();
            }
            liftmotor.setPower(0);
        } else if (positionChange < 0) {
            liftmotor.setPower(-power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() > targetPosition) {
                Thread.yield();
            }
            liftmotor.setPower(0);
        }
    }

    void strafeRightByEncoder(double inches, DcMotor motor, double power) {
        power = Math.abs(power);
        double positionChange = COUNTS_PER_INCH * inches;
        int oldPosition = motor.getCurrentPosition();
        double targetPosition = oldPosition + positionChange;

        if (positionChange > 0) {
            strafeRight(power);
            while (opMode.opModeIsActive() && -motor.getCurrentPosition() < targetPosition) {
                Thread.yield();
            }
            driveStop();
        } else if (positionChange < 0) {
            strafeRight(-power);
            while (opMode.opModeIsActive() && -motor.getCurrentPosition() > targetPosition) {
                Thread.yield();
            }
            driveStop();
        }

    }
    void turnClockwiseByEncoder (double inches, DcMotor motor, double power){
        double positionChange = COUNTS_PER_INCH * inches;
        power = Math.abs(power);
        int oldPosition = motor.getCurrentPosition();
        double targetPosition = oldPosition + positionChange;

        if (positionChange > 0) {
            turnClockwise(power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() < targetPosition) {
                Thread.yield();
            }
            driveStop();
        } else if (positionChange < 0) {
            turnClockwise(-power);
            while (opMode.opModeIsActive() && motor.getCurrentPosition() > targetPosition) {
                Thread.yield();
            }
            driveStop();
        }
    }


}